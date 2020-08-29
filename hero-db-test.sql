-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema herodb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema herodb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `herodbtest` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `herodbtest` ;

-- -----------------------------------------------------
-- Table `herodb`.`superPower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`superPower` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `herodb`.`character`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`character` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `superPower_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_character_superPower_idx` (`superPower_id` ASC) VISIBLE,
  CONSTRAINT `fk_character_superPower`
    FOREIGN KEY (`superPower_id`)
    REFERENCES `herodbtest`.`superPower` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `herodb`.`organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`organization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(75) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `herodb`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(75) NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `herodb`.`character_has_organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`character_has_organization` (
  `character_id` INT NOT NULL,
  `organization_id` INT NOT NULL,
  PRIMARY KEY (`character_id`, `organization_id`),
  INDEX `fk_character_has_organization_organization1_idx` (`organization_id` ASC) VISIBLE,
  INDEX `fk_character_has_organization_character1_idx` (`character_id` ASC) VISIBLE,
  CONSTRAINT `fk_character_has_organization_character1`
    FOREIGN KEY (`character_id`)
    REFERENCES `herodbtest`.`character` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_character_has_organization_organization1`
    FOREIGN KEY (`organization_id`)
    REFERENCES `herodbtest`.`organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `herodb`.`character_has_location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`character_has_location` (
  `character_id` INT NOT NULL,
  `location_id` INT NOT NULL,
  `date` DATETIME(4) NULL,
  PRIMARY KEY (`character_id`, `location_id`),
  INDEX `fk_character_has_location_location1_idx` (`location_id` ASC) VISIBLE,
  INDEX `fk_character_has_location_character1_idx` (`character_id` ASC) VISIBLE,
  CONSTRAINT `fk_character_has_location_character1`
    FOREIGN KEY (`character_id`)
    REFERENCES `herodbtest`.`character` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_character_has_location_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `herodbtest`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
