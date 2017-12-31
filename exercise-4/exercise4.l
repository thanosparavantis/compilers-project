variable_type (real|integer|boolean|char)
identifier [a-zA-z][a-zA-z0-9_]*
reserved_keyword var
space [" "\t]
new_line \n+
%%
{reserved_keyword}({space}+{identifier}(","{space}*{identifier})*":"{space}*{variable_type}";")+ {
    printf("\nThe follow variable declaration is valid! \n%s", yytext);
}
%%
