variable_type (real|integer|boolean|char)
identifier [a-zA-z][a-zA-z0-9_]*
starting_keyword var
space [" "\t]
new_line \n

%%
{starting_keyword}{space}({space}*{identifier}(","{space}*{identifier})*":"{space}*{variable_type}";"({new_line})?)+({new_line})$ {
    printf("\nThe following variable declaration is valid! \n%s", yytext);
}
%%