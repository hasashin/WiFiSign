# WiFiSign v1.1

WiFiSign is simple Bukkit Plugin. It allows you to connect far points on map with redstone connection without delays and distance restrictions. Plugin works on Bukkit based server version 1.14. Plugin wasn't tested on any previous relases of Bukkit server.

## Usage

To use redstone WiFi simply hang sign on any block (it has to be __Oak Sign__) and place a lever on top of that block. Sign needs to contain these values:
- first line: "[wifi]"
- secong line: network name
- third line _(optional)_: operation mode 

All signs with the same network name working together. If any of these signs gets powered, levers are manipulated, to copy power state.

## Operation mode

There are three modes of operation:
- IN
- OUT
- IO _(default)_

__IN__ mode turns off lever toggling for sign on which is written. _IN_ sign can be used when building one-way signal and not needing lever on one site.  
__OUT__ mode disables power checking on sign so powering _OUT_ sign with redstone doesn't change anything in network.  
__IO__ mode is default operation mode, when sign accepts power and sends it to lever. You can left third line empty to create _IO_ sign.

---
© 2019 Dominik Hażak