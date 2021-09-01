# MConfig

## About
MConfig is a minimal config api that allows you to easily work with you config files using gson. 
MConfig is responsible for loading, saving and creating config files and gives you full control 
over the data.

## Usage
### Setup
build.gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    // replace VERSION with the correct version
    implementation "com.github.mattidragon:mconfig:VERSION"
}
```

To create a config just call `Config.of(String)` with your mods mod id (required) and store it 
in a field. If you want multiple configs use `Config.of(Identifier)` with a namespace of your 
mod id and a path of the config file name. Create a config will load it. 

Before you can start using the config you need to create a default file. If you used the string 
creation method it should be called `config.json` and be placed in the "resources" folder. If you 
used the identifier constructor, it should be placed in `config/PATH.json` in the "resources" 
folder.

### Reading the config
To read a value use the `getOption(String)` method. It takes a dot separated string that 
specifies the location of the option. If you need to edit it at runtime, you use the 
`setOption(String, JsonElement)` method. Remember to save it afterwards using `save()`.

### Mod Menu
If you want to use the builtin modmenu screen you can make your modmenu entrypoint like this:
```java
@Override
public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
    // CONFIG is the name of the field where the config is stored
    return CONFIG::createScreen;
}
```

### Example
```java
public class Example implements ModMenuApi {
    public static final Config CONFIG = Config.of(new Identifier("example", "main"));
    public static final Config CONFIG2 = Config.of("example");
    
    @Override
    public void onInitialize() {
        System.out.println(CONFIG.getOption("object.field").getAsInt());
        System.out.println(CONFIG2.getOption("hello").getAsString());
        CONFIG2.setOption("hello", new JsonPrimitive("wrld"));
        CONFIG2.save();
        System.out.println(CONFIG2.getOption("hello").getAsString().equals("wrld"));
        CONFIG2.setOption("hello", new JsonPrimitive("world"));
    }
    
    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return CONFIG::createScreen;
    }
}
```

But I would recommend that you make your own as the build in one is quite limited.

## Limits
Currently, all options have to be both in the default and users config. This means maps and 
similar structures can't be implemented. It also causes optional values to be impossible. This 
might change in the future if I switch away from json or do something else.