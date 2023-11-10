<!--
    Copyright (C) 2016 Gary Gregory. All rights reserved.

    See the NOTICE.txt file distributed with this work for additional
    information regarding copyright ownership.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
-->
Apache HttpComponents Core
==========================

This is a patch, for fulfilling specific inspection requirement, of the [Apache HttpComponents Core](https://github.com/apache/httpcomponents-core) project.

Usage
-----

```kotlin
implementation("io.github.sunny-chung:httpcore5:5.2-inspect-patch1")
implementation("io.github.sunny-chung:httpcore5-h2:5.2-inspect-patch1")
```

Licensing
---------

Apache HttpComponents Core is licensed under the Apache License 2.0.
See the files [LICENSE.txt](./LICENSE.txt) and [NOTICE.txt](./NOTICE.txt) for more information.

Cryptographic Software Notice
-----------------------------

This distribution may include software that has been designed for use
with cryptographic software. The country in which you currently reside
may have restrictions on the import, possession, use, and/or re-export
to another country, of encryption software. BEFORE using any encryption
software, please check your country's laws, regulations and policies
concerning the import, possession, or use, and re-export of encryption
software, to see if this is permitted. See https://www.wassenaar.org/
for more information.

The U.S. Government Department of Commerce, Bureau of Industry and
Security (BIS), has classified this software as Export Commodity
Control Number (ECCN) 5D002.C.1, which includes information security
software using or performing cryptographic functions with asymmetric
algorithms. The form and manner of this Apache Software Foundation
distribution makes it eligible for export under the License Exception
ENC Technology Software Unrestricted (TSU) exception (see the BIS
Export Administration Regulations, Section 740.13) for both object
code and source code.

The following provides more details on the included software that
may be subject to export controls on cryptographic software:

> Apache HttpComponents Core interfaces with the
> Java Secure Socket Extension (JSSE) API to provide
> - HTTPS support
> 
> Apache HttpComponents Core does not include any
> implementation of JSSE.
