variable_type (real|integer|boolean|char)
identifier [a-zA-z][a-zA-z0-9_]*
reserved_keyword var
space [" "\t]
new_line \n
anything_else .
%%
{reserved_keyword}{space}({space}*{identifier}(","{space}*{identifier})*":"{space}*{variable_type}";"({new_line})?)+({new_line})$ {
    printf("\nThe following variable declaration is valid! \n%s", yytext);
}
%%