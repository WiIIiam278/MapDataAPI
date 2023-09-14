# MapDataAPI
[![Build Status](https://github.com/WiIIiam278/MapDataAPI/actions/workflows/ci.yml/badge.svg)](https://github.com/WiIIiam278/MapDataAPI/actions/workflows/ci.yml)
[![Discord](https://img.shields.io/discord/818135932103557162?color=7289da&logo=discord)](https://discord.gg/tVYhJfyDWG)
[![Maven](https://repo.william278.net/api/badge/latest/releases/net/william278/mapdataapi?color=00fb9a&name=Maven&prefix=v)](https://repo.william278.net/#/releases/net/william278/mapdataapi/)

**MapDataAPI** is a platform-agnostic Java API for interfacing with _Minecraft: Java Edition_ map data files. It supports the following operations:
* Reading map data from [an NBT file](https://minecraft.fandom.com/wiki/Map_item_format)
* Reading a buffered image and converting it to map data by approximating colors
* Writing a map file to a bitmap image on disk
* Reading and writing map data to a compressed NBT format, for use in other applications, such as letting map data persist across multiple Minecraft servers on a proxy network

## Building
To build MapDataAPI with Java 16+, run the following in the root directory:
```shell
./gradlew clean build
```

## License
MapDataAPI is licensed under [Apache 2.0 License](https://github.com/WiIIiam278/MapDataAPI/blob/master/LICENSE).

---
&copy; [William278](https://william278.net/), 2022. Licensed under the Apache 2.0 License.