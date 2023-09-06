--liquibase formatted sql
--changeset <postgres>:<create-currency-rate-table>

CREATE TABLE IF NOT EXISTS public.currency_rate
(
    id bigint  NOT NULL,
    currency_name character varying(256) NOT NULL,
    rate decimal  NOT NULL,
    CONSTRAINT currency_rate_pk PRIMARY KEY (id)
    );

--rollback DROP TABLE currency_rate;