/*Copyright [2014] [Harshvardhan J Pandit]

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License. */

package msc.prototype.context;

/**
 * SystemToken generates a token
 * that is used to access private fields inside Context Objects
 * This is used because the classes are in public domain and outside
 * android's own system package
 * @author harsh
 * @version  1.0
 */
class SystemToken {
    /**
     * only a single token accessible using SystemToken.token
     */
    static SystemToken token = new SystemToken();

    /**
     * private constructor so no further objects can be created
     */
    private SystemToken() {

    }
}
