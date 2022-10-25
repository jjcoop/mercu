This is a [Next.js](https://nextjs.org/) project bootstrapped with [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app).

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

You can start editing the page by modifying `pages/index.js`. The page auto-updates as you edit the file.

[API routes](https://nextjs.org/docs/api-routes/introduction) can be accessed on [http://localhost:3000/api/hello](http://localhost:3000/api/hello). This endpoint can be edited in `pages/api/hello.js`.

The `pages/api` directory is mapped to `/api/*`. Files in this directory are treated as [API routes](https://nextjs.org/docs/api-routes/introduction) instead of React pages.

## Learn More

To learn more about Next.js, take a look at the following resources:

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
- [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js/) - your feedback and contributions are welcome!

## Deploy on Vercel

The easiest way to deploy your Next.js app is to use the [Vercel Platform](https://vercel.com/new?utm_medium=default-template&filter=next.js&utm_source=create-next-app&utm_campaign=create-next-app-readme) from the creators of Next.js.

Check out our [Next.js deployment documentation](https://nextjs.org/docs/deployment) for more details.

## Allow start to run on ports under 1500
```
 sudo setcap cap_net_bind_service=+ep `readlink -f \`which node\``
```
## Change the localhost in .env
```
NEXT_PUBLIC_DB_HOST="localhost"
```


## Configure EC2

```
sudo apt update && \
sudo apt install openjdk-8-jdk -y && \
sudo apt install curl -y && \
sudo apt install git -y && \
sudo apt install nodejs npm -y && \

sudo setcap cap_net_bind_service=+ep `readlink -f \`which node\``

curl https://archive.apache.org/dist/kafka/2.8.0/kafka_2.13-2.8.0 tgz \ 
--output kafka.tgz 

tar -xvzf kafka.tgz

cd kafka && \
./bin/zookeeper-server-start.sh ./config/zookeeper.properties 
./bin/kafka-server-start.sh ./config/server.properties
./bin/kafka-topics.sh --bootstrap-server=localhost:9092 --list
./bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:cklog-topic --from-beginning9092 --topic ba
```
## Run the mvn and start jar's
```
java -jar ./backend_jar/procurems-0.0.1-SNAPSHOT.jar
java -jar ./backend_jar/inventoryms-0.0.1-SNAPSHOT.jar
java -jar ./backend_jar/salems-0.0.1-SNAPSHOT.jar
java -jar ./backend_jar/bi-0.0.1-SNAPSHOT.jar


```