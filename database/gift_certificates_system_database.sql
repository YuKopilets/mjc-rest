CREATE SCHEMA IF NOT EXISTS `gift_certificates_system` DEFAULT CHARACTER SET utf8 ;
USE `gift_certificates_system` ;

-- -----------------------------------------------------
-- Table `gift_certificates_system`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`tag` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  `duration` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`gift_certificate_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate_has_tag` (
  `gift_certificate_id` BIGINT(20) UNSIGNED NOT NULL,
  `tag_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`gift_certificate_id`, `tag_id`),
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
    FOREIGN KEY (`gift_certificate_id`)
    REFERENCES `gift_certificates_system`.`gift_certificate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gift_certificate_has_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `gift_certificates_system`.`tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

