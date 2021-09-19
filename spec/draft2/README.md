# Config Definition - Draft 2
## A Definition
A definition of an element. It contains the field `type` which describes its type limits. It can 
be an array of strings or a string. 

It also contains a `default` field, unless specified 
otherwise. It contains the default value used while generating the config. 

The definition can optionally contain the `allowNull` field, which specifies whether the value `null` is allowed. 
It defaults to `false`. 

Depending on the `type` field they may contain other optional and/or required fields, defined 
by the type.

## Types

- [Simple](simple_types) (int, integer)
  - [Number and Integer](simple_types#Number and Integer) (num, number, float, double)
  - [String](simple_types#String) (str, string)
  - [Boolean](simple_types#Boolean) (bool, boolean)
  - [Enum](simple_types#Enum) (enum)
- [Collections](collection_types)
  - [List](collection_types#List) (list)
  - [Dictionary](collection_types#Dictionary) (dict, dictionary)
  - [Map](collection_types#Map) (map)
- [Organisation](organisation_types)
  - [Category](organisation_types#Category) (cat, category)
  
| Names              | Java Type      |
|:------------------:|:--------------:|
| int, integer       | Integer        |
| num, number, float | Double         |
| str, string        | String         |
| bool, boolean      | Boolean        |
| cat, category      | _custom_       |
| dict, dictionary   | Map<String, V> |
| map                | Map<K, V>      |
| list               | List<V>        |
| null               | null           |

## Size and Range
Many types support this field, and it contains many subfields (more may be added later) and thus 
is defined separately. 

### Optional Subfields
| Name       | Type      | Description 
|:----------:|:---------:|:------------
| min        | int       | The minimum size (inclusive)
| max        | int       | The maximum size (exclusive)
| multipleOf | list<num> | The size must be a multiple of all numbers in this list

## Example
see [Example](example)

