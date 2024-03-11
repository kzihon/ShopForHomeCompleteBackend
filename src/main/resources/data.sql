-- Insert dummy ImageModel data
INSERT INTO image_model (name, type, file_path) VALUES
("pinkHeartChair.png", "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/pinkHeartChair.png"),
('wooden3legSideTable.png', "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/wooden3legSideTable.png"),
("whiteSideTable.png", "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/whiteSideTable.png"),
('flowerLamp.png', "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/flowerLamp.png"),
("greyLamp.png", "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/greyLamp.png"),
('artificialFiscusTree.png', "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/artificialFiscusTree.png"),
("fairyLightPlant.png", "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/fairyLightPlant.png"),
('setOf3LargePaintings.png', "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/setOf3LargePaintings.png"),
("largeClock.png", "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/largeClock.png"),
('brownCouch.png', "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/brownCouch.png"),
("blueVelvetCouch.png", "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/blueVelvetCouch.png"),
('kidsBearRug.png', "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/kidsBearRug.png"),
("vintageRug.png", "image/png", "/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/vintageRug.png");

-- Insert dummy Product data
INSERT INTO products (product_id, name, price, description, category, number_in_stock, supplier, image_model_id) VALUES
(1000, 'Pink Heart Chair', 50.99, 'Description for Pink Heart', 'CHAIRS', 50, 'Supplier1', 1),
(1001, 'Wooden Side Table', 40.99, 'Wooden side table', 'CHAIRS', 8, 'Supplier1', 2),
(1002, 'White Side Table', 30.99, 'Description for white side table', 'CHAIRS', 50, 'Supplier1', 3),
(1003, 'Flower Lamp', 22.99, 'USB Powered LED Rose Tree Lamp - Perfect for Home Decor, Parties, and Weddings!', 'LAMPS', 30, 'Lamp Supplier', 4),
(1004, 'Grey Lamp', 20.99, 'Description for white side table', 'LAMPS', 30, 'Lamp Supplier', 5),
(1005, 'Artificial Fiscus Tree', 80.99, 'Large Artificial Fiscus Tree for decoration', 'PLANTS', 15, 'Supplier2', 6),
(1006, 'Fairy Lights Vine', 10.99, 'Description for pretty fairy lights', 'PLANTS', 15, 'Supplier3', 7),
(1007, 'Set of 3 Large Paintings', 110.99, 'Description for Product4', 'DECOR', 20, 'Supplier4', 8),
(1008, 'Large Clock', 150.99, 'Description for Product5', 'DECOR', 20, 'Supplier5', 9),
(1009, 'Brown Couch', 200.50, 'Description for Product6', 'COUCHES', 5, 'Supplier6', 10),
(1110, 'Velvet Blue Couch', 330.50, 'Description for Product7', 'COUCHES', 2, 'Supplier7', 11),
(1111, 'Bear Rug for Nursery', 50.50, 'Description for Product7', 'RUGS', 9, 'Supplier7', 12),
(1112, 'Vintage Large Rug', 80.50, 'Description for Product7', 'RUGS', 20, 'Supplier7', 13);

INSERT INTO coupon (id, coupon_name, code, discount, active) VALUES
                                                            (100,'Loyal Customer Discount', 'LOYAL10', 10.0, true),
                                                            (101,'Military Discount', 'MILITARY15', 15.0, true),
                                                            (102,'Holiday Special', 'HOLIDAY25', 25.0, true),
                                                            (103,'Corporate Professional Discount', 'CORP10', 10.0, true),
                                                            (104,'TechPro', 'TECH20', 20.0, true),
                                                            (105,'Healthcare Heroes Appreciation', 'HERO15', 15.0, true),
                                                            (106,'Birthday Celebration', 'BIRTHDAY5', 5.0, true);