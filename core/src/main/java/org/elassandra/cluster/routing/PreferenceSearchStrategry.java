/*
 * Copyright (c) 2017 Strapdata (http://www.strapdata.com)
 * Contains some code from Elasticsearch (http://www.elastic.co)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.elassandra.cluster.routing;

import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.routing.ShardRoutingState;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.Index;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Search strategy 
 * @author vroyer
 *
 */
public class PreferenceSearchStrategry extends CachedRandomSearchStrategy {
    
    public PreferenceSearchStrategry() {
        
    }
    
    public class SearchKeyRouter extends CachedRandomRouter {
        public SearchKeyRouter(final Index index, final String ksName, BiFunction<Index, UUID, ShardRoutingState> shardsFunc, final ClusterState clusterState) {
            super(index, ksName, shardsFunc, clusterState);
        }
        
        public Key getKey(TransportAddress src, @Nullable String searchKey, @Nullable String preference) {
            return new Key(null, ((preference!=null) && (preference.charAt(0)!='_')) ? preference : null);
        }
    }
    
    @Override
    public Router newRouter(final Index index, final String ksName, BiFunction<Index, UUID, ShardRoutingState> shardsFunc, final ClusterState clusterState) {
        return new SearchKeyRouter(index, ksName, shardsFunc, clusterState);
    }
}
