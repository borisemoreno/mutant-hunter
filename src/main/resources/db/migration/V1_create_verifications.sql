create table dna_verifications
(
    id uuid unique not null
        constraint dna_verifications_pk
            primary key,
    dna text not null,
    mutant boolean not null
);

create unique index dna_verifications_dna_uindex on dna_verifications (dna);