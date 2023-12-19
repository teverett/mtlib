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
   : LIST ID NUM (item | width | EMPTY)* ENDLIST
   ;

width
   : WDITH NUM
   ;

item
   : ITEM ID NUM*
   ;

WDITH
   : 'Width'
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

