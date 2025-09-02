# ESP8266 Smart Home Automation (Software/Simulation Version)

## Description
A Wi-Fi-based smart home controller using ESP8266. You can **simulate control** of lights, fan, AC, door locks, and curtains remotely via HTTP requests.  

This project focuses on **software logic**, including:

- Motor and relay control logic
- Debouncing for reliable sensor readings  
- HTTP API commands  

**Note:** This version does **not** include actual physical connections to appliances. The ESP8266 code sends control signals as if relays and motors were connected.

---

## Pin Configuration (Simulation / Future Hardware)

| Device                 | ESP8266 Pin |
|------------------------|-------------|
| Lights                 | D0          |
| Fan                    | D1          |
| AC                     | D2          |
| Door Lock Signal       | D3          |
| Door Unlock Signal     | D4          |
| Curtain Motor UP       | D5          |
| Curtain Motor DOWN     | D6          |
| Curtain Limit Signal   | D8          |

---

## Software Setup

1. Install **Arduino IDE**  
2. Install **ESP8266 Board support** in Arduino IDE  
3. Connect **ESP8266 via USB**  
4. Update Wi-Fi credentials in the code (`ssid` and `password`)  
5. Upload the code to ESP8266  

---

## API / HTTP Commands (Simulation Mode)

**Lights**  
- `/lights/on` → Turn ON  
- `/lights/off` → Turn OFF  

**Fan**  
- `/fan/on` → Turn ON  
- `/fan/off` → Turn OFF  

**AC**  
- `/ac/ON` → Turn ON  
- `/ac/OFF` → Turn OFF  

**Door**  
- `/door/LOCKED` → Lock door  
- `/door/UNLOCKED` → Unlock door  

**Curtains**  
- `/curtains/UP` → Move UP  
- `/curtains/DOWN` → Move DOWN  
- `/curtains/STOP` → Stop motor  

---

## Future Expansion / Hardware Implementation

### Hardware Implementation
To turn this into a real smart home system, the following hardware connections are required:

- ESP8266 Output Pins: Connecting each ESP8266 GPIO output to a relay module that controls an appliance (lights, fan, AC).
- Curtain Motor Control: Wiring the curtain motor through two relays to swap polarity, controlling rotation direction (UP and DOWN).
- Limit Switches: Installing top and bottom limit switches on the curtain track, connecting them to ESP8266 GPIOs to automatically stop the motor.
- Electronic Door Lock/Unlock Actuators: Connecting actuators to appropriate DC power supply modules (derived from main AC via adapter or transformer), and control them via relays triggered by the ESP8266.
- Power Supplies:
  - ESP8266 Board: 5 V DC supply.
  - Curtain Motor & Door Actuators: Separate DC supply modules, converting 220 V AC to required DC voltage for each device.
