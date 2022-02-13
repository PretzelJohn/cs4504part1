<h1>CS 4504 Project (Part 1)</h1>
<h6>by John Hussey, Ryan Kim, Nick Nguyen, and David Shipman</h6>

<h2>Description</h2>
<p>This is a group class project to investigate and analyze client/server networks.</p>
<p>This project requires the use of <em>at least</em> 3 separate computers, that are connected on a local network.</p>
<ul>
    <li>Router: 1 computer will act as a router, which passes messages between all connected clients and servers.</li>
    <li>Server: At least 1 computer will act as a server, which receives messages from a client and returns the message to the client (in all caps).</li>
    <li>Client: At least 1 other computer will act as a client, which sends a list of messages (read from file.txt) to a server, and measures the time it takes to receive the server's response.</li>
</ul>

<h2>Usage</h2>
<p>To run this project successfully, you will need at least 3 computers available that have Java 17 installed. Check which version of Java you have installed by running <code>java --version</code> in command prompt (windows) or terminal (Linux, Mac). Once those requirements are met, follow the instructions below for each computer.</p>

<h3>All</h3>
<p>Do the following on ALL computers that will be used:</p>
<ol>
    <li>Open command prompt (Windows) or terminal (Linux, Mac)</li>
    <li>Make note of your local IPv4 (i.e. 192.168.0.27) address, which you can find with <code>ipconfig</code>(windows) or <code>ifconfig -a | grep "inet "</code>(Linux, Mac)</li>
</ol>

<h3>Router</h3>
<p>On the computer that will act as a router, do the following:</p>
<ol>
    <li>Download <b>Router.jar</b> and <b>config.yml</b> from the latest release into a folder</li>
    <li>Change your directory in command prompt or terminal to the download folder (command: <code>cd [path]</code>)</li>
    <li>Run the command <code>java -jar Router.jar</code> in command prompt or terminal</li>
</ol>
<p>If successful, you should see the message: <code>ServerRouter is Listening on port: 5555</code></p>

<h3>Server</h3>
<p>On the computer(s) that will act as a server, do the following:</p>
<ol>
    <li>Download <b>Server.jar</b> and <b>config.yml</b> from the latest release into a folder</li>
    <li>Open the downloaded <b>config.yml</b> file in a text editor</li>
    <li>Change the IP next to <code>router-ip:</code> to the IP address of the <i>router</i></li>
    <li>Change the IP next to <code>destination:</code> to the IP address of the <i>client</i></li>
    <li>Save <b>config.yml</b></li>
    <li>Run the command <code>java -jar Server.jar</code> in command prompt or terminal</li>
</ol>
<p>If successful, you should see the message: <code>ServerRouter: Connected to the router.</code></p>

<h3>Client</h3>
<p>On the computer(s) that will act as a server, do the following:</p>
<ol>
    <li>Download <b>Client.jar</b>, <b>config.yml</b>, and <b>file.txt</b> from the latest release into a folder</li>
    <li>Open the downloaded <b>config.yml</b> file in a text editor</li>
    <li>Change the IP next to <code>router-ip:</code> to the IP address of the router</li>
    <li>Change the IP next to <code>destination:</code> to the IP address of the <i>server</i></li>
    <li>Save <b>config.yml</b></li>
    <li>Run the command <code>java -jar Client.jar</code> in command prompt or terminal</li>
</ol>
<p>If successful, you should see the message: <code>ServerRouter: Connected to the router.</code><br>
After several seconds, you should then see the following messages:</p>
<code>Server: 127.0.0.1</code><br>
<code>Cycle time: 10000</code><br>
<code>Client: This is a message.</code><br>
<code>Server: THIS IS A MESSAGE.</code><br>
<code>Cycle time: 20</code><br>
<code>Client: This is another message.</code><br>
<code>Server: THIS IS ANOTHER MESSAGE.</code><br>
<code>Cycle time: 17</code><br>
<code>Client: Bye.</code>
