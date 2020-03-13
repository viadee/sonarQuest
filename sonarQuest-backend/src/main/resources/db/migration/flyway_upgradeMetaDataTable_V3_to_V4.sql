DROP INDEX "${schema}"."${table}_vr_idx";
DROP INDEX "${schema}"."${table}_ir_idx";
ALTER TABLE "${schema}"."${table}" DROP COLUMN "version_rank";
ALTER TABLE "${schema}"."${table}" DROP CONSTRAINT "${table}_pk";
ALTER TABLE "${schema}"."${table}" ALTER COLUMN "version" SET NULL;
ALTER TABLE "${schema}"."${table}" ADD CONSTRAINT "${table}_pk" PRIMARY KEY ("installed_rank");
UPDATE "${schema}"."${table}" SET "type"='BASELINE' WHERE "type"='INIT';