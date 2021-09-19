# Example
## Definition
```yaml
properties:
  a:
    type: "string"
    regex: "[a-zA-Z0-9]+"
    size: 
      min: 1
      max: 10
  cat:
    type: "category"
    properties:
      field: 
        type: "number"
        size: 
          min: 0
          multipleOf: 
            - 3
            - 5
  dict:
    type: "dictionary"
    key:
      size: 
        min: 3
    value:
      type: "number"
  mapping:
    type: "map" 
    key: 
      type: "number"
    value: 
      type: "category"
      properties:
        a:
          type: "number"
        b: 
          type: "string"
  list:
    type: "list"
    element:
      type: [int, bool, string]
```

## Possible Config
```yaml
a: "abcABC123"
cat:
  field: 15
dict:
  hel: 19 # dict can contain any number of elements
  test: 93 # keys must be strings, while values can be anything
mapping:
  - key: 10 # map can contain any number of elements.
    value: # keys and values can be of any type.
      a: 10
      b: "hello"
  - key: 11
    value: 
      a: 1
      b: "world 10"
```