grammar Inventory;

file
   : lines END EOF
   ;

lines
   : line+
   ;

line
   : listline
   ;

listline
   : LIST ID NUM (item | EMPTY)* ENDLIST
   ;

item
   : ITEM ID NUM*
   ;

EMPTY
   : 'Empty'
   ;

ITEM
   : 'Item'
   ;

LIST
   : 'List'
   ;

ENDLIST
   : 'EndInventoryList'
   ;

END
   : 'EndInventory'
   ;

ID
   : [a-zA-Z:_]+
   ;

NUM
   : [0-9]+
   ;

WS
   : [ \r\n\t]+ -> skip
   ;

