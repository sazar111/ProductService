-- Insert Sample Categories
INSERT INTO categories (id, title) VALUES
                                       (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 'Electronics'),
                                       (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 'Fashion'),
                                       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 'Home & Kitchen'),
                                       (UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')), 'Sports & Outdoors'),
                                       (UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), 'Books');

-- Insert Sample Products
INSERT INTO products (id, title, price, category_id, description, image, ratings) VALUES
                                                                                      (UNHEX(REPLACE('a1b2c3d4-0001-0001-0001-aaaaaaaaaaaa', '-', '')), 'Smartphone', 999,
                                                                                       UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 'Latest AI-powered smartphone.', 'smartphone.jpg', 4.5),

                                                                                      (UNHEX(REPLACE('a1b2c3d4-0002-0002-0002-aaaaaaaaaaaa', '-', '')), 'Laptop', 1500,
                                                                                       UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 'High-performance gaming laptop.', 'laptop.jpg', 4.7),

                                                                                      (UNHEX(REPLACE('a1b2c3d4-0006-0006-0006-aaaaaaaaaaaa', '-', '')), 'T-Shirt', 25,
                                                                                       UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), '100% Cotton graphic T-shirt.', 'tshirt.jpg', 4.2),

                                                                                      (UNHEX(REPLACE('a1b2c3d4-0011-0011-0011-aaaaaaaaaaaa', '-', '')), 'Coffee Maker', 110,
                                                                                       UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 'Automatic drip coffee maker.', 'coffeemaker.jpg', 4.7),

                                                                                      (UNHEX(REPLACE('a1b2c3d4-0016-0016-0016-aaaaaaaaaaaa', '-', '')), 'Backpack', 60,
                                                                                       UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')), 'Waterproof with multiple compartments.', 'backpack.jpg', 4.4),

                                                                                      (UNHEX(REPLACE('a1b2c3d4-0021-0021-0021-aaaaaaaaaaaa', '-', '')), 'Cookbook', 20,
                                                                                       UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), 'Best-selling recipes.', 'cookbook.jpg', 4.3);
