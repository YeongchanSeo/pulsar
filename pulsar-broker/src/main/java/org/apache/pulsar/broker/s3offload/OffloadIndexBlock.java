/**
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
 */
package org.apache.pulsar.broker.s3offload;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import org.apache.bookkeeper.client.api.LedgerMetadata;
import org.apache.bookkeeper.common.annotation.InterfaceStability.Unstable;

/**
 *
 * The Index block abstraction used for offload a ledger to long term storage.
 *
 */
@Unstable
public interface OffloadIndexBlock extends Closeable {

    /**
     * Get the content of the index block as InputStream.
     * Read out in format:
     *   | index_magic_header | index_block_len | index_entry_count |
     *   | data_object_size | segment_metadata_length | segment metadata | index entries ... |
     */
    InputStream toStream() throws IOException;

    /**
     * Get the related OffloadIndexEntry that contains the given messageEntryId.
     *
     * @param messageEntryId
     *                      the entry id of message
     * @return the offload index entry
     */
    OffloadIndexEntry getIndexEntryForEntry(long messageEntryId) throws IOException;

    /**
     * Get the entry count that contained in this index Block.
     */
    int getEntryCount();

    /**
     * Get LedgerMetadata.
     */
    LedgerMetadata getLedgerMetadata();

    /**
     * Get the total size of the data object.
     */
    long getDataObjectLength();
}
