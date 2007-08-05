/*
 * $HeadURL$
 * $Revision$
 * $Date$
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

package org.apache.http.impl.nio.reactor;

import java.net.InetSocketAddress;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Simple tests for {@link DefaultListeningIOReactor}.
 *
 * @author <a href="mailto:oleg at ural.ru">Oleg Kalnichevski</a>
 * 
 * @version $Id$
 */
public class TestDefaultListeningIOReactor extends TestCase {

    // ------------------------------------------------------------ Constructor
    public TestDefaultListeningIOReactor(String testName) {
        super(testName);
    }

    // ------------------------------------------------------------------- Main
    public static void main(String args[]) {
        String[] testCaseName = { TestDefaultListeningIOReactor.class.getName() };
        junit.textui.TestRunner.main(testCaseName);
    }

    // ------------------------------------------------------- TestCase Methods

    public static Test suite() {
        return new TestSuite(TestDefaultListeningIOReactor.class);
    }

    public void testRestart() throws Exception {
        HttpParams params = new BasicHttpParams();
        
        DefaultListeningIOReactor ioReactor = new DefaultListeningIOReactor(1, params);
        ioReactor.listen(new InetSocketAddress(9999));
        ioReactor.shutdown();
        
        ioReactor = new DefaultListeningIOReactor(1, params);
        ioReactor.listen(new InetSocketAddress(9999));
        ioReactor.shutdown();         
    }
    
}
