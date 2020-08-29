-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema superherosightings
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `heroDBtest` ;

-- -----------------------------------------------------
-- Schema superherosightings
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `heroDBtest` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
-- -----------------------------------------------------
-- Schema herodb
-- -----------------------------------------------------
USE `heroDBtest` ;

-- -----------------------------------------------------
-- Table `superherosightings`.`superPower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroDBtest`.`superPower` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superherosightings`.`character`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroDBtest`.`character` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `superPower_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_hero_superPower1_idx` (`superPower_id` ASC) VISIBLE,
  CONSTRAINT `fk_hero_superPower1`
    FOREIGN KEY (`superPower_id`)
    REFERENCES `heroDBtest`.`superPower` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superherosightings`.`organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroDBtest`.`organization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(75) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superherosightings`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroDBtest`.`location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(75) NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superherosightings`.`character_has_organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroDBtest`.`character_has_organization` (
  `character_id` INT NOT NULL,
  `organization_id` INT NOT NULL,
  PRIMARY KEY (`character_id`, `organization_id`),
  INDEX `fk_character_has_organization_organization1_idx` (`organization_id` ASC) VISIBLE,
  INDEX `fk_character_has_organization_character1_idx` (`character_id` ASC) VISIBLE,
  CONSTRAINT `fk_character_has_organization_character1`
    FOREIGN KEY (`character_id`)
    REFERENCES `heroDBtest`.`character` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_character_has_organization_organization1`
    FOREIGN KEY (`organization_id`)
    REFERENCES `heroDBtest`.`organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superherosightings`.`location_has_character`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heroDBtest`.`location_has_character` (
  `location_id` INT NOT NULL,
  `character_id` INT NOT NULL,
  `date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `character_id`),
  INDEX `fk_location_has_character_character1_idx` (`character_id` ASC) VISIBLE,
  INDEX `fk_location_has_character_location1_idx` (`location_id` ASC) VISIBLE,
  CONSTRAINT `fk_location_has_character_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `heroDBtest`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_has_character_character1`
    FOREIGN KEY (`character_id`)
    REFERENCES `heroDBtest`.`character` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
