-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema Hero
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Hero
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Hero` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `Hero` ;

-- -----------------------------------------------------
-- Table `Hero`.`hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero`.`hero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `superPower` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero`.`organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero`.`organization` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `descrption` VARCHAR(255) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero`.`location` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip` VARCHAR(45) NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero`.`hero_has_organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero`.`hero_has_organization` (
  `hero_id` INT NOT NULL,
  `organization_id` INT NOT NULL,
  PRIMARY KEY (`hero_id`, `organization_id`),
  INDEX `fk_hero_has_organization_organization1_idx` (`organization_id` ASC) VISIBLE,
  INDEX `fk_hero_has_organization_hero_idx` (`hero_id` ASC) VISIBLE,
  CONSTRAINT `fk_hero_has_organization_hero`
    FOREIGN KEY (`hero_id`)
    REFERENCES `Hero`.`hero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hero_has_organization_organization1`
    FOREIGN KEY (`organization_id`)
    REFERENCES `Hero`.`organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero`.`hero_has_location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero`.`hero_has_location` (
  `hero_id` INT NOT NULL,
  `location_id` INT NOT NULL,
  `date` DATETIME NULL,
  PRIMARY KEY (`hero_id`, `location_id`),
  INDEX `fk_hero_has_location_location1_idx` (`location_id` ASC) VISIBLE,
  INDEX `fk_hero_has_location_hero1_idx` (`hero_id` ASC) VISIBLE,
  CONSTRAINT `fk_hero_has_location_hero1`
    FOREIGN KEY (`hero_id`)
    REFERENCES `Hero`.`hero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hero_has_location_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `Hero`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
