verb        (Δ|δ)(ί|ι)νεται
triangle    τρ(ί|ι)γωνο
square      τετρ(ά|α)γωνο
corner      γων(ί|ι)α
%%
{verb}" "({triangle}|{square}|{corner}) {
    printf("%s είναι ρήμα \n", yytext);
    printf("%s είναι γεωμετρική οντότητα \n", yytext);
}
%%