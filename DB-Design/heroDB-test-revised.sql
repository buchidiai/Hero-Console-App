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
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `herodb`.`hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`hero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `superPower_id` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_hero_superPower_idx` (`superPower_id` ASC) VISIBLE,
  CONSTRAINT `fk_hero_superPower`
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
-- Table `herodb`.`hero_has_organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`hero_has_organization` (
  `hero_id` INT NOT NULL,
  `organization_id` INT NOT NULL,
  PRIMARY KEY (`hero_id`, `organization_id`),
  INDEX `fk_hero_has_organization_organization1_idx` (`organization_id` ASC) VISIBLE,
  INDEX `fk_hero_has_organization_hero1_idx` (`hero_id` ASC) VISIBLE,
  CONSTRAINT `fk_hero_has_organization_hero1`
    FOREIGN KEY (`hero_id`)
    REFERENCES `herodbtest`.`hero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hero_has_organization_organization1`
    FOREIGN KEY (`organization_id`)
    REFERENCES `herodbtest`.`organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `herodbtest` ;

-- -----------------------------------------------------
-- Table `herodb`.`sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `herodbtest`.`sighting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL,
  `location_id` INT NOT NULL,
  `hero_id` INT NOT NULL,
  PRIMARY KEY (`id`, `location_id`, `hero_id`),
  INDEX `fk_sighting_location_idx` (`location_id` ASC) VISIBLE,
  INDEX `fk_sighting_hero1_idx` (`hero_id` ASC) VISIBLE,
  CONSTRAINT `fk_sighting_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `herodbtest`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sighting_hero1`
    FOREIGN KEY (`hero_id`)
    REFERENCES `herodbtest`.`hero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
