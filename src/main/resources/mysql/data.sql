INSERT INTO tag(name) VALUES
                          ('KID_FRIENDLY'),
                          ('FREE'),
                          ('RESTAURANT'),
                          ('MUSEUM'),
                          ('ENTERTAINMENT'),
                          ('HISTORY');

INSERT INTO tourist_attraction(name, description, city, price) VALUES
                                                                   ('Nyhavn', 'En historisk havn med farverige huse, restauranter og caféer', 'COPENHAGEN', 0),
                                                                   ('ARoS Kunstmuseum', 'Et moderne kunstmuseum kendt for den cirkulære regnbue på taget', 'AARHUS', 150),
                                                                   ('Den Gamle By', 'Et frilandsmuseum der viser dansk byliv gennem historien', 'AARHUS', 120),
                                                                   ('Odense Zoo', 'En populær zoologisk have med dyr fra hele verden', 'ODENSE', 100),
                                                                   ('Aalborg Zoo', 'En familievenlig zoologisk have med fokus på naturbevarelse', 'AALBORG', 150),
                                                                   ('Legoland Billund', 'En forlystelsespark bygget af LEGO-klodser for hele familien', 'BILLUND', 299),
                                                                   ('Ribe Vikingecenter', 'Et levende museum der genskaber vikingernes liv og hverdag', 'ESBJERG', 150),
                                                                   ('Frederiksborg Slot', 'Et imponerende renæssanceslot omgivet af søer og haver', 'HILLEROED', 99.95),
                                                                   ('Himmelbjerget', 'Et af Danmarks højeste punkter med flot udsigt over landskabet', 'SILKEBORG', 0),
                                                                   ('H.C. Andersens Hus', 'Et museum dedikeret til forfatteren H.C. Andersens liv og værker', 'ODENSE', 115);

INSERT INTO attraction_tag(attraction_id, tag_id) VALUES
                                                      (1,2),(1,6),
                                                      (2,4),(2,5),
                                                      (3,4),(3,6),(3,1),
                                                      (4,1),(4,5),
                                                      (5,1),(5,5),
                                                      (6,1),(6,5),
                                                      (7,6),(7,4),(7,1),
                                                      (8,6),(8,4),
                                                      (9,2),(9,6),
                                                      (10,4),(10,6);