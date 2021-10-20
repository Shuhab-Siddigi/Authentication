# Java rmi authentication application

```plantuml

node Client as C
artifact Stub as st
artifact Skeleton as sk
hexagon Network as N
node Servers as S

C <--> st
st <-> N
N <-> sk
sk <-u-> S
  
```
