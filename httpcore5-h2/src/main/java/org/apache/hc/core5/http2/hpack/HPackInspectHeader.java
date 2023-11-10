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

/**
 * Internal HPack header representation that also contains binary length of
 * header name and header value.
 */
final public class HPackInspectHeader extends HPackHeader {
    public static final String PSEUDO_HEADER_KEY_DYNAMIC_TABLE_SIZE_UPDATE = ":::update-dynamic-table-size";

    private final Format format;

    private final Integer index;

    HPackInspectHeader(final String name, final int nameLen, final String value, final int valueLen, final boolean sensitive, final Format format, final Integer index) {
        super(name, nameLen, value, valueLen, sensitive);
        this.format = format;
        this.index = index > 0 ? index : null;
    }

    public HPackInspectHeader(final String name, final String value, final boolean sensitive, final Format format, final Integer index) {
        super(name, value, sensitive);
        this.format = format;
        this.index = index > 0 ? index : null;
    }

    HPackInspectHeader(final HPackHeader header, final Format format, final Integer index) {
        super(header.getName(), header.getNameLen(), header.getValue(), header.getValueLen(), header.isSensitive());
        this.format = format;
        this.index = index > 0 ? index : null;
    }

    public Format getFormat() {
        return format;
    }

    public Integer getIndex() {
        return index;
    }

    public enum Format {
        INDEXED, LITERAL_WITH_INDEXING, LITERAL_WITHOUT_INDEXING, LITERAL_NEVER_INDEXED, OTHER;

        static HPackInspectHeader.Format fromLiteralRepresentation(final HPackRepresentation representation) {
            switch (representation) {
                case WITH_INDEXING:
                    return HPackInspectHeader.Format.LITERAL_WITH_INDEXING;
                case WITHOUT_INDEXING:
                    return HPackInspectHeader.Format.LITERAL_WITHOUT_INDEXING;
                case NEVER_INDEXED:
                    return HPackInspectHeader.Format.LITERAL_NEVER_INDEXED;
            }
            throw new UnsupportedOperationException();
        }
    }
}
