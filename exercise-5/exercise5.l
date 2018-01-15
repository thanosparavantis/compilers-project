%{
#include <stdio.h>
#include <string.h>

logger(char *msg);
%}

verb        (D|d)inetai
line        e(y|u)thia
triangle    trig(w|o)no
square      tetrag(w|o)no
corner      g(w|o)nia
poly        pentag(w|o)no
letters     [A-E]


%%
{verb}" "({triangle}|{corner})" "({letters}{3})$ {
    logger(yytext);
}

{verb}" "({corner})" "({letters}{1})$ {
    logger(yytext);
}

{verb}" "{line}" "({letters}{2})$ {
    logger(yytext);
}

{verb}" "{square}" "({letters}{4})$ {
    logger(yytext);
}

{verb}" "{poly}" "({letters}{5})$ {
    logger(yytext);
}

%%

logger(char *msg) {
    char *p = strtok (msg, " ");
    char *split_msg[3];
    int i = 0;
    int valid_name = 1;

    while (p != NULL)
    {
        split_msg[i++] = p;
        p = strtok (NULL, " ");
    }

    char *a = split_msg[2];
    int arr_len = sizeof(a) / sizeof(a[0]);

    for (int i = 0; i < arr_len - 1; i++) { 
        for (int j = i + 1; j < arr_len; j++) {
            if (a[i] == a[j] && a[i] != NULL && a[j] != NULL) {
                valid_name = 0;
            }
        }
    }

    if (valid_name == 1) {
        printf("%s: einai rhma.\n", split_msg[0]);
        printf("%s: einai gewmetrikh ontothta\n", split_msg[1]);
        printf("%s: einai onoma gewmetrikhs ontothtas\n", split_msg[2]);
    }
}

int main() {
    yylex();
    return 0;
}