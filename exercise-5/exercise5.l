%{
#include <stdio.h>
#include <string.h>

logger(char *msg);
%}

verb        (Δ|δ)(ί|ι)νεται
line        ευθ(ί|ι)α
triangle    τρ(ί|ι)γωνο
square      τετρ(ά|α)γωνο
corner      γων(ί|ι)α
poly        πεντ(ά|α)γωνο
letters     (Α|Β|Γ|Δ|Ε)


%%
{verb}" "({triangle}|{corner})" "{letters}{3} {
    logger(yytext);
}

{verb}" "{line}" "{letters}{2} {
    logger(yytext);
}

{verb}" "{square}" "{letters}{4} {
    logger(yytext);
}

{verb}" "{poly}" "{letters}{5} {
    logger(yytext);
}

%%

logger(char *msg) {
    char *p = strtok (msg, " ");
    char *split_msg[3];
    int i = 0;

    while (p != NULL)
    {
        split_msg[i++] = p;
        p = strtok (NULL, " ,.-");
    }

    printf("%s: είναι ρήμα.\n", split_msg[0]);
    printf("%s: είναι γεωμετρική οντότητα\n", split_msg[1]);
    printf("%s: είναι όνομα γεωμετρική οντότητα\n", split_msg[2]);
}

int main() {
    yylex();
    return 0;
}