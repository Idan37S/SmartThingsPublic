/**
 *  Virtual Presence Sensor Plus v1.0
 *
 *  Author: 
 *    Idan Slonimsky (idan37s)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
    definition (name: "Virtual Presence Sensor Plus", namespace: "idan37s", author: "Idan Slonimsky") {
        capability "Presence Sensor"
        capability "Sensor"
        capability "Switch"
        capability "Actuator"

        command "departed"
        command "almost_arrived"
        command "arrived"
    }

    simulator {
        status "not present": "presence: not present"
        status "near": "presence: near"
        status "present": "presence: present"
    }

    tiles(scale: 2) {
        standardTile("presence", "device.presence", width: 3, height: 3, canChangeBackground: true) {
            state "not present", label:'Not Present', action:"almost_arrived", icon:"st.presence.tile.not-present", backgroundColor:"#ffffff", nextState:"near"
            state "near", label:'Near', action:"arrived", icon:"st.presence.tile.presence-default", backgroundColor:"#b3ebff", nextState:"present"
            state "present", label:'Present', action:"departed", icon:"st.presence.tile.present", backgroundColor:"#00a0dc", nextState:"not present"
        }
        standardTile("button", "device.switch", width: 3, height: 3, canChangeIcon: false,  canChangeBackground: true) {
			state "off", label: 'Not Present', action: "on", icon: "st.presence.tile.not-present", backgroundColor: "#ffffff", nextState:"on"
			state "on", label: 'Present', action: "off", icon: "st.presence.tile.present", backgroundColor: "#00a0dc", nextState:"off"
		}
		
        main("presence")
        details(["presence", "button"])
    }
}

def parse(String description) {
    def pair = description.split(":")
    createEvent(name: pair[0].trim(), value: pair[1].trim())
}

// handle commands
def almost_arrived() {
    sendEvent(name: "presence", value: "near")
}

def arrived() {
	sendEvent(name: "switch", value: "on")
    sendEvent(name: "presence", value: "present")
}

def departed() {
	sendEvent(name: "switch", value: "off")
    sendEvent(name: "presence", value: "not present")
}

def on() {
	arrived()
}

def off() {
	departed()
}
