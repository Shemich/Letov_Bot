# Letov's Poems Telegram Bot
![87- Converted](https://sun9-31.userapi.com/impg/8eVemjs8FVitercvS8WvJhMqQb8w-68QCjqghA/CKomx-f5bOU.jpg?size=620x620&quality=96&sign=b0614ee5521d67373f86707315680169&type=album)

Shows poems by Yegor Letov in random order. 

<p align='left'>
   <a href="https://t.me/LetovPoem_bot">
       <img src="https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white"/>
   </a>
<p align='left'>
</p>

LetovPoem telegram bot gives poems from an open source [Egor Letov's Poems](https://www.gr-oborona.ru/pub/rock/egor_letov_stihi.html) 2003 year edition.

## Idea
The basic idea is that we upload the book in solid text without using a database. And then read poems randomly by pressing just one button, and add them to favorites. Personally, I'm too lazy to take a big heavy book off the shelf or search for poems on the Internet. In addition, it can be used as a poem of a new day, as a fortune-telling story.

## How it works
We see asterisks in the original book as the separation of fragments of the whole text. We will use them to divide everything and put each poem in an array.

## MVP Scope
As a user, I want to receive poems in random order and add to favourite.

# How it works 
Based on MVP Scope, we can specify next behaviours (here and after Telegram User, which is using LetovPoem Bot will call User):
- User can get poems in random order.
- User can add poem in favourites.
- User can delete a poem from favourites.

## Deployment
This bot can be deployed to Heroku using the heroku-buildpack-java.

# Local development
For local development and testing, use ngrok.

First, we need to start the server that will listen for letov bot's webhooks.

To do that, we're going to use ngrok.

This will create a server on your device, running on the port 5000.

Next, run ngrok http 5000 with the command line. This creates a public address for your app (in this case our webhooks bot). Copy the address ngrok gives you in the console (should look like randomnumbers.ngrok.io). 

Paste it into your browser and verify that everything is working, you should see "online" in the browser.

Add your telegram bot token to the botToken variable in application.properties

Add your ngrok https url to the webHookPath variable in application.properties

See the [Telegram Bot API documentation](https://core.telegram.org/bots/api#getting-updates) for a description of the Bot API interface and a complete list of available classes, methods and updates.

Send an HTTPS POST request to the specified url, containing a JSON-serialized Update:

```https://api.telegram.org/botBOT_TOKEN/setWebhook?url=NGROK_HTTPS```

Get current webhook status:

```https://api.telegram.org/botBOT TOKEN/getWebhookInfo```

Next step, run SpringBoot main method.

# Technological stack
- SpringBoot as a skeleton framework
- Telegrambots Spring Boot Starter
- MongoDB database as a database for saving user and poems info

## Screenshot
![alt text](https://sun9-45.userapi.com/impg/TCqEpvX2HUcAlf0_iKJLREwsmn7V3MyKXHCfvQ/F5XLo7fDnXI.jpg?size=773x1040&quality=96&proxy=1&sign=1978f03cc3ec9060439c4c127925238c)


## License
This project is MIT License - see the [LICENSE](LICENSE) file for details

# Contributions
Feel free to suggest new features via [github issue](https://github.com/Shemich/Letov_bot/issues/new).
Note, that new features must be approved before start implement it to avoid the situation, when the time was spent, but the changes wouldn't added to the project.
