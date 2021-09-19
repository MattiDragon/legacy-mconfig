# Simple Types

## Number and Integer
The corresponding values are always of type `number` in the config file. If the type is `int` or
`integer` it must be an integer.

### Optional Fields

| Name | Description
|:---:|-----
| `range` | A range restriction (see [Size and Range](README.md#Size%20and%20Range))

## String
The corresponding values are `string`s in the config file.

### Optional Fields

| Name | Description
|:---:|-----
| `regex` | Contains a regex pattern that the string must match.
| `size` | A size restriction (see [Size and Range](README.md#Size%20and%20Range))

## Boolean
The corresponding values are of type `boolean` in the config file. Different formats have
different possible values. Defaults will be parsed as they are parsed in yaml.

## Enum 
Enums can be stored as with all types. They allow users to define types with a specific set of 
possible values. It's recommended to use simple types.

### Required Fields

| Name | Description
|:---:|-----
| `values` | A array of possible values. Elements can be of any type.