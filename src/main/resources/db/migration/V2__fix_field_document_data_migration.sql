ALTER TABLE public.document DROP COLUMN document_data;
ALTER TABLE public.document ADD COLUMN document_data bytea;