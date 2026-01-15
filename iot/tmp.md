Processes in contiki:
- Cooperative
- Need to call `sleep` to give up resources
- Only woken up by events

# Network stack
## Radio layer
`NERSTACK_RADIO`
Abstracts the layer to handle different kinds of radio

## RDC layer
`NERSTACK_RDC`
Handles when the radio is powered on or not

## MAC layer
`NERSTACK_MAC`
CSMA, etc

## Network layer
`NETSTACL_NETWORK`
Ipv6 etc.

# Rime stack address
2 bytes address provided by the radio. (the radio actually gives a 8 byte one but we only take the last two)
`linkaddr_node_addr.u8[]`

```cpp
broadcast_open(
        &connection,    //
        CHANNEL,        // Not a network channel. Just a logical one
        &callbacks      // Pointer to a struct that contains two callbacks (send and receive)
        );

broadcast_send(&conn);
```

## Packet Buffer
We have **one** packet buffer we use for both sending and receiving.
1. `packetbuf_clear()`
2. `packetbuf_copyfrom(*from, len)`
3. `broadcast_send(&connection)` (or other )

Other useful function
- packetbuf_datalen

# Data serialization
The main problems are *byte order*, *unaligned access*, and *structure padding*.

## Byte order
Solved by deciding a priori an ordering that all devices in the network should use.

## Memory alignment
The CPU can work with multi-byte numbers only when they are placed at the word boundary.
The compiler always aligns variables, but we cannot assume the value *in a payload* do be aligned.

## Structure Padding
This struct on a 32bit CPU is gonna take **12 bytes** even tho there are only 7 bytes of data.
```c
struct {
    uint8_t  a;
    uint32_t b;
    uint16_t c;
}
```
It is because `a` and `c` are gonna be padded to 32bits.
We can use `__attribute__((packed))` to tell the compiler not to pad the fields.

Packing the struct like this reduces the impact we have on the network (taking up less bandwidth due to the smaller packet size) but can lead to less efficient code on the single machines.
The compiler when working with "unpadded" data cannot work at word level anymore but has to work at byte level. 

## Serialization (marshaling)
The actual solution to these problems is serialization. We need to define a serialization and de-serialization function.
These should convert between struct and byte-stream and vice versa.

> When assigning a multi-byte value to a byte variable the least significant byte is taken and the rest truncated.


