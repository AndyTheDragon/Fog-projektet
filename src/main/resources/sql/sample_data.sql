insert into carport_material (material_name, width, height, length) VALUES
                                                                        ('25x200 mm. trykimp. bræt', 25, 200, 540),
                                                                        ('25x200 mm. trykimp. bræt', 25, 200, 360),
                                                                        ('25x150 mm. trykimp. bræt', 25, 150, 480),
                                                                        ('25x150 mm. trykimp. bræt', 25, 150, 540),
                                                                        ('25x150 mm. trykimp. bræt', 25, 150, 300),
                                                                        ('25x125 mm. trykimp. bræt', 25, 125, 540),
                                                                        ('25x125 mm. trykimp. bræt', 25, 125, 360),
                                                                        ('25x50 mm. trykimp. bræt', 25, 50, 540),
                                                                        ('97x97 mm. trykimp. stolpe', 100, 100, 300),
                                                                        ('45x195 mm. spærtræ ubh.', 50, 200, 600),
                                                                        ('45x195 mm. spærtræ ubh.', 50, 200, 540),
                                                                        ('45x195 mm. spærtræ ubh.', 50, 200, 480),
                                                                        ('45x195 mm. spærtræ ubh.', 50, 200, 300),
                                                                        ('45x95 mm. reglar ubh.', 50, 100, 240),
                                                                        ('45x95 mm. reglar ubh.', 50, 100, 270),
                                                                        ('45x95 mm. reglar ubh.', 50, 100, 330),
                                                                        ('19x100 mm. trykimp. bræt', 20, 100, 540),
                                                                        ('19x100 mm. trykimp. bræt', 20, 100, 480),
                                                                        ('19x100 mm. trykimp. bræt', 20, 100, 360),
                                                                        ('19x100 mm. trykimp. bræt', 20, 100, 240),
                                                                        ('19x100 mm. trykimp. bræt', 20, 100, 210),
                                                                        ('38x73 mm. taglægte T1', 40, 75, 540),
                                                                        ('38x73 mm. taglægte T1', 40, 75, 360),
                                                                        ('38x73 mm. taglægte T1', 40, 75, 420);
insert into carport_material (material_name, unit) VALUES
                                                                        ('plastmo bundskruer 200 stk.', 'pakke'),
                                                                        ('hulbånd 1x20 mm. 10 mtr.', 'rulle'),
                                                                        ('universal 190 mm højre', 'stk'),
                                                                        ('universal 190 mm venstre', 'stk'),
                                                                        ('4,5 x 60 mm skruer 200 stk.', 'pakke'),
                                                                        ('4,0 x 50 mm beslagskruer 250 stk.', 'pakke'),
                                                                        ('bræddebolt 10 x 120 mm', 'stk'),
                                                                        ('firkantskiver 40x40x11 mm', 'stk'),
                                                                        ('4,5 x 70 mm skruer 400 stk.', 'pakke'),
                                                                        ('4,5 x 50 mm skruer 300 stk.', 'pakke'),
                                                                        ('stalddørsgreb 50x75', 'sæt'),
                                                                        ('t hængsel 390 mm', 'stk'),
                                                                        ('vinkelbeslag 35', 'stk');                                                                        ;

insert into material_function (description) values
                                                ('understernbrædder', 'wood'),
                                                ('oversternbrædder', 'wood'),
                                                ('til z på bagside af dør', 'wood'),
                                                ('løsholter til skur gavle', 'wood'),
                                                ('løsholter til skur sider', 'wood'),
                                                ('Remme i sider, sadles ned i stolper', 'wood'),
                                                ('Spær, monteres på rem', 'wood'),
                                                ('Stolper nedgraves 90 cm. i jord', 'wood'),
                                                ('til beklædning af skur 1 på 2', 'wood'),
                                                ('beklædning af gavle 1 pà 2', 'wood'),
                                                ('vandbrædt på stern i sider', 'wood'),
                                                ('vandbrædt på stern i forende', 'wood'),
                                                ('tagplader monteres på spær', 'roof'),
                                                ('vandbrædt på vindskeder', 'wood'),
                                                ('Vindskeder pa rejsning', 'wood'),
                                                ('til montering oven pà tagfodslægte', 'wood'),
                                                ('til montering på spær, 7 rækker lægter på hver side', 'wood'),
                                                ('toplagte til montering af rygsten lægges i toplagte beslag', 'wood');
insert into material_function (description) values
                                                ('Skruer til tagplader', 'screw'),
                                                ('Til vindkryds på spær', 'screw'),
                                                ('Til montering af spær på rem', 'screw'),
                                                ('Til montering af stern&vandbrædt', 'screw'),
                                                ('Til montering af universalbeslag + hulbånd', 'screw'),
                                                ('Til montering af rem på stolper', 'screw'),
                                                ('til montering af yderste beklædning', 'screw'),
                                                ('til montering af inderste beklædning', 'screw'),
                                                ('Til lås på dør i skur', 'screw'),
                                                ('Til skurdør', 'screw'),
                                                ('Til montering af løsholter i skur', 'screw');

insert into carport_material_function (material_id, function_id) VALUES
             (1,1),
             (2,1),
             (6,2),
             (7,2),
             (24,3),
             (15,4),
             (14,5),
             (10, 6),
             (11,6),
             (12,6),
             (10, 7),
             (11,7),
             (12,7),
             (13,7),
             (9,8),
             (21,9),
             (17,10),
             (18,10),
             (19,10);
insert into carport_material_function (material_id, function_id) VALUES
             (23,3),
             (22,3),
             (25,19),
             (26,19),
             (27,20),
             (28,21),
             (29,21),
             (30,22),
             (31,23),
             (32,24),
             (33,24),
             (34,25),
             (35,26),
             (36,27),
             (37,28),
             (38,29);




insert into account (user_name, email, user_password) VALUES ('Morten', 'mortens@fog.dk', 'fog2024$1');
insert into customer (customer_name, address, zipcode, city, phone_number, email) VALUES  ('Lars', 'Tegnevej 8', '4400', 'Kalundborg', '22332244', 'kunde@wyrmlings.dk');


insert into carport_order (customer_id, sales_id, carport_width, carport_length, carport_height, carport_shed, shed_width, shed_length, carport_roof)
VALUES (1, null, 600, 780, 230, true, 530, 210, 'flat'),
       (1, null, 300, 480, 230, true, 230, 110, 'flat'),
       (1, null, 300, 540, 230, false, 0, 0, 'flat');
