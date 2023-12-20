# mtlib

A Java library for reading and writing [Minetest](https://www.minetest.net/) Redis databases from [Redis](https://www.redis.com/)

## License

mtlib is distributed under the [2-clause BSD license](https://opensource.org/license/bsd-2-clause/).

## Usage

````
<dependency>
    <groupId>com.khubla.mtlib</groupId>
    <artifactId>mtlib</artifactId>
    <version>1.0.0</version>
</dependency>
````

### Creating database configuration object

````
BaseDatabaseConfig myDatabaseConfig = new BaseDatabaseConfig();
myDatabaseConfig.setHost("localhost");
myDatabaseConfig.setPort(6379);
myDatabaseConfig.setHash("mt");
myDatabaseConfig.setPassword("mypassword");
````

### Instantiating WorldMap

````
WorldMap worldMap = new DefaultWorldMap(myDatabaseConfig);
````

### Query

````
// get Block at 0,0,0
Block block = worldMap.getBlock(new Coord(0,0,0));

// get node at 10,20,30
Node node = worldMap.getNode(new Coord(10,20,30))

// set node type to dirt
short dirtId = BlockTypes.getInstance().getId("default:dirt")
node.setParam0(dirtId);
worldMap.setNode(new Coord(10,20,30), node);
````