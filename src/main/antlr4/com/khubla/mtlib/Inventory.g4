grammar Inventory;

file
   : list* END EOF
   ;

list
   : LIST ID NUM listitem+ ENDLIST
   ;

listitem
   : (item | EMPTY)
   | width
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

