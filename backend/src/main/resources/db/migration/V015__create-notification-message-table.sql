CREATE TABLE notification_message
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    source_profile_id BIGINT                NULL,
    target_profile_id BIGINT                NULL,
    article_id        BIGINT                NULL,
    send_username     VARCHAR(255)          NULL,
    type              SMALLINT              NULL,
    profile_id        BIGINT                NOT NULL,
    created_at        datetime              NULL,
    CONSTRAINT pk_notification_message PRIMARY KEY (id)
);

ALTER TABLE notification_message
    ADD CONSTRAINT FK_NOTIFICATION_MESSAGE_ON_PROFILE FOREIGN KEY (profile_id) REFERENCES profiles (user_id);