verb        (Δ|δ)(ί|ι)νεται
line        ευθ(ί|ι)α
triangle    τρ(ί|ι)γωνο
square      τετρ(ά|α)γωνο
corner      γων(ί|ι)α
poly        πεντ(ά|α)γωνο
letters     [ΑΒΓ]
%%
{verb}" "({triangle}|{square}|{corner}|{poly}|{line})" "{letters} {
    printf("%s :είναι ρήμα\n", yytext);
    printf("%s :είναι γεωμετρική οντότητα\n", yytext);
    printf("%s :είναι όνομα γεωμετρική οντότητα\n", yytext);
}
%%