CREATE TABLE categories
(
    id                 BINARY(16)   NOT NULL,
    last_modified_date datetime NULL,
    created_date       datetime NULL,
    title              VARCHAR(255) NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE products
(
    id                 BINARY(16)   NOT NULL,
    last_modified_date datetime NULL,
    created_date       datetime NULL,
    title              VARCHAR(255) NULL,
    price              INT NOT NULL,
    category_id        BINARY(16)   NULL,
    `description`      VARCHAR(255) NULL,
    image              VARCHAR(255) NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);