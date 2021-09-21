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

- [Simple](simple_types.md) (int, integer)
  - [Number and Integer](simple_types.md#Number%20and%20Integer) (num, number, float, double)
  - [String](simple_types.md#String) (str, string)
  - [Boolean](simple_types.md#Boolean) (bool, boolean)
  - [Enum](simple_types.md#Enum) (enum)
- [Collections](collection_types.md)
  - [List](collection_types.md#List) (list)
  - [Dictionary](collection_types.md#Dictionary) (dict, dictionary)
  - [Map](collection_types.md#Map) (map)
- [Organisation](organisation_types.md)
  - [Category](organisation_types.md#Category) (cat, category)

<!--- Might be used later:
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
-->

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
see [Example](example.md)

