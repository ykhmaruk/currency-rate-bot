# Telegram bot for receiving the exchange rate of the NBU

This telegram bot is designed to receive the current exchange rate of the National Bank of Ukraine (NBU) relative to the
Ukrainian hryvnia. It provides users with a convenient way to find out the exchange rate of the selected currency.

## Usage

1. First of all, you need to create a bot in Telegram (here is the link with instruction
   `https://core.telegram.org/bots/features#creating-a-new-bot`) and use it for this application. See how to run the
   application using the Docker below.

2. Write a message to the bot with the name of the currency (in Ukrainian) you want to check. For example, to get the
   US dollar rate, type "Долар США".

3. The bot will return you the current exchange rate of the selected currency, information about the update date.

## Technologies:

• Programming Language: Java 17;

• Maven 4.0.0;

• Spring Boot 3.1.3;

• Spring MVC 6.0.11;

• Spring Data JPA;

• Telegram bot API 6.8.0;

• Lombok: 1.18.20;

• Mapstruct 1.5.5.Final;

• Testcontainers 1.16.2;

• Liquibase 4.20.0;

• Docker 24.0.2;

• PostgreSQL 15.

## Project Launch with Docker

1. Install Docker if you don't have (here is the link `https://www.docker.com/products/docker-desktop/`);
2. Clone the repository from GitHub;
3. Create a `.env` file with the necessary environment variables.(as an example for filling - `.env.sample`);
4. Run `mvn clean package` command;
5. Run `docker-compose up --build` command to build and start the Docker containers;
6. Get started with your Telegram bot.
