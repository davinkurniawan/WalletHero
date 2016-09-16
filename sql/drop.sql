ALTER TABLE "user" DROP CONSTRAINT IF EXISTS "user_fk0";

ALTER TABLE "transaction" DROP CONSTRAINT IF EXISTS "transaction_fk0";

ALTER TABLE "transaction" DROP CONSTRAINT IF EXISTS "transaction_fk1";

ALTER TABLE "transaction" DROP CONSTRAINT IF EXISTS "transaction_fk2";

ALTER TABLE "session" DROP CONSTRAINT IF EXISTS "session_fk0";

ALTER TABLE "user_detail" DROP CONSTRAINT IF EXISTS "user_detail_fk0";

ALTER TABLE "user_detail" DROP CONSTRAINT IF EXISTS "user_detail_fk1";

ALTER TABLE "goal" DROP CONSTRAINT IF EXISTS "goal_fk0";

ALTER TABLE "goal" DROP CONSTRAINT IF EXISTS "goal_fk1";

DROP TABLE IF EXISTS "user";

DROP TABLE IF EXISTS "transaction";

DROP TABLE IF EXISTS "session";

DROP TABLE IF EXISTS "user_detail";

DROP TABLE IF EXISTS "category";

DROP TABLE IF EXISTS "occupation";

DROP TABLE IF EXISTS "status";

DROP TABLE IF EXISTS "recurrence";

DROP TABLE IF EXISTS "goal";
