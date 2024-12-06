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
insert into material_function (description) values
                                                ('understernbrædder'),
                                                ('oversternbrædder'),
                                                ('til z på bagside af dør'),
                                                ('løsholter til skur gavle'),
                                                ('løsholter til skur sider'),
                                                ('Remme i sider, sadles ned i stolper'),
                                                ('Spær, monteres på rem'),
                                                ('Stolper nedgraves 90 cm. i jord'),
                                                ('til beklædning af skur 1 på 2'),
                                                ('beklædning af gavle 1 pà 2'),
                                                ('vandbrædt på stern i sider'),
                                                ('vandbrædt på stern i forende'),
                                                ('tagplader monteres på spær'),
                                                ('vandbrædt på vindskeder'),
                                                ('Vindskeder pa rejsning'),
                                                ('til montering oven pà tagfodslægte'),
                                                ('til montering på spær, 7 rækker lægter på hver side'),
                                                ('toplagte til montering af rygsten lægges i toplagte beslag');
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
             (19,10),
             (20,11),
             (18,11),
             (19,11);



insert into account (user_name, email, user_password) VALUES ('Morten', 'mortens@fog.dk', 'fog2024$1');
insert into customer (customer_name, address, zipcode, city, phone_number, email) VALUES  ('Lars', 'Tegnevej 8', '4400', 'Kalundborg', '22332244', 'kunde@wyrmlings.dk');


insert into carport_order (customer_id, sales_id, carport_width, carport_length, carport_height, carport_shed, shed_width, shed_length, carport_roof)
VALUES (1, null, 600, 780, 230, true, 530, 210, 'flat'),
       (1, null, 300, 480, 230, true, 230, 110, 'flat'),
       (1, null, 300, 540, 230, false, 0, 0, 'flat');
