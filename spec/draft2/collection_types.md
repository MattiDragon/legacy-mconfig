# Collection Types

## List 
Lists are represented as `array`s. They store many of the same type of object.

### Optional Fields:

| Name | Description
|:---:|-----
| `size` | A size restriction (see [Size and Range](README.md#Size%20and%20Range))
| `allowDuplicates` | Whether or not to allow duplicates in the list. The default value is `true`.

### Required Fields

| Name | Description
|:---:|-----
| `elements` | Contains the definition of the elements.

## Dictionary
They are represented as objects where keys are keys and values are values of the dictionary. 
They're used to simply represent maps with string keys and highly dynamic categories.

### Optional Fields

| Name | Description
|:---:|-----
| `key` | Contains a definition of the key strings. Can't contain a `type` field as it's always string.
| `size` | A size restriction (see [Size and Range](README.md#Size%20and%20Range))
| `allowDuplicates` | Whether or not to allow duplicates in the list. The default value is `true`.

### Required Fields

| Name | Description
|:---:|-----
| `value` | Contains the definition of the values.

## Map 
Maps are stored as `array`s of `object`s that have `key` and `value` properties. They correspond 
to they keys and values of the map entries.

### Optional Fields

| Name | Description
|:---:|-----
| `size` | A size restriction (see [Size and Range](README.md#Size%20and%20Range))
| `allowDuplicates` | Whether or not to allow duplicates in the list. The default value is `true`.


### Required Fields

| Name | Description
|:---:|-----
| `value` | Contains the definition of the values.
| `key` | Contains a definition of the keys.
