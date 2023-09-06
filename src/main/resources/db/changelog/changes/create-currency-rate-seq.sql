--liquibase formatted sql
--changeset <postgres>:<create-currency-rate-id>
CREATE SEQUENCE IF NOT EXISTS public.currency_rate_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.currency_rate_id_seq