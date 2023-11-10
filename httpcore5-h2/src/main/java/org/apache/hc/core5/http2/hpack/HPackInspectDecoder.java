/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.hc.core5.http2.hpack;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import static org.apache.hc.core5.http2.hpack.HPackInspectHeader.PSEUDO_HEADER_KEY_DYNAMIC_TABLE_SIZE_UPDATE;

public class HPackInspectDecoder extends HPackDecoder {
    HPackInspectDecoder(final InboundDynamicTable dynamicTable, final CharsetDecoder charsetDecoder) {
        super(dynamicTable, charsetDecoder);
    }

    HPackInspectDecoder(final InboundDynamicTable dynamicTable, final Charset charset) {
        super(dynamicTable, charset);
    }

    public HPackInspectDecoder(final Charset charset) {
        super(charset);
    }

    public HPackInspectDecoder(final CharsetDecoder charsetDecoder) {
        super(charsetDecoder);
    }

    @Override
    HPackHeader decodeIndexedHeader(final ByteBuffer src) throws HPackException {
        final int index = decodeInt(src, 7);
        final HPackHeader existing =  this.dynamicTable.getHeader(index);
        if (existing == null) {
            throw new HPackException("Invalid header index");
        }
        return new HPackInspectHeader(existing, HPackInspectHeader.Format.INDEXED, index);
    }

    @Override
    HPackHeader decodeLiteralHeader(final ByteBuffer src, final HPackRepresentation representation) throws HPackException, CharacterCodingException {
        final int n = representation == HPackRepresentation.WITH_INDEXING ? 6 : 4;
        final int index = decodeInt(src, n);
        final String name;
        final int nameLen;
        if (index == 0) {
            final StringBuilder buf = new StringBuilder();
            nameLen = decodeString(src, buf);
            name = buf.toString();
        } else {
            final HPackHeader existing =  this.dynamicTable.getHeader(index);
            if (existing == null) {
                throw new HPackException("Invalid header index");
            }
            name = existing.getName();
            nameLen = existing.getNameLen();
        }
        final StringBuilder buf = new StringBuilder();
        final int valueLen = decodeString(src, buf);
        final String value = buf.toString();
        final HPackHeader header = new HPackInspectHeader(name,
                nameLen,
                value,
                valueLen,
                representation == HPackRepresentation.NEVER_INDEXED,
                HPackInspectHeader.Format.fromLiteralRepresentation(representation),
                index
        );
        if (representation == HPackRepresentation.WITH_INDEXING) {
            this.dynamicTable.add(header);
        }
        return header;
    }

    @Override
    HPackHeader decodeHPackHeader(final ByteBuffer src) throws HPackException {
        try {
            while (src.hasRemaining()) {
                final int b = peekByte(src);
                if ((b & 0x80) == 0x80) {
                    return decodeIndexedHeader(src);
                } else if ((b & 0xc0) == 0x40) {
                    return decodeLiteralHeader(src, HPackRepresentation.WITH_INDEXING);
                } else if ((b & 0xf0) == 0x00) {
                    return decodeLiteralHeader(src, HPackRepresentation.WITHOUT_INDEXING);
                } else if ((b & 0xf0) == 0x10) {
                    return decodeLiteralHeader(src, HPackRepresentation.NEVER_INDEXED);
                } else if ((b & 0xe0) == 0x20) {
                    final int maxSize = decodeInt(src, 5);
                    this.dynamicTable.setMaxSize(Math.min(this.maxTableSize, maxSize));
                    return new HPackInspectHeader(PSEUDO_HEADER_KEY_DYNAMIC_TABLE_SIZE_UPDATE, 0, "" + maxSize, 0, false, HPackInspectHeader.Format.OTHER, maxSize);
                } else {
                    throw new HPackException("Unexpected header first byte: 0x" + Integer.toHexString(b));
                }
            }
            return null;
        } catch (final CharacterCodingException ex) {
            throw new HPackException(ex.getMessage(), ex);
        }
    }
}
