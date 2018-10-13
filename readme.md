# Coding Excise

We are interested in seeing your coding, problem solving and collaboration style. We would like you to build the following system. When you come to meet us for your programming interview, bring along your laptop or files, as you will be pairing with a employee who will work with you on your solution to complete a stretch goal.

For the purpose of this exercise, company is in the process of rewriting its job ads checkout system.
We want to offer different products to recruiters:

| Name   | Description  | Retail |
|--------|--------------|--------|
| Classic Ad | Offers the most basic level of advertisement | $269.99 |
| Stand out Ad | Allows advertisers to use a company logo and use a longer presentation text | $322.99 |
| Premium Ad   | Same benefits as Standout Ad, but also puts the advertisement at the top of the results, allowing higher visibility | $394.99 |

We have established a number of special pricing rules for a small number of privileged customers:

1. SecondBite
	- Gets a **3 for 2** deal on **Classic Ads**
2. Axil Coffee Roasters
	- Gets a discount on **Stand out Ads** where the price drops to **$299.99** per ad
3. MYER
	- Gets a **5 for 4** deal on **Stand out Ads**
	- Gets a discount on **Premium Ads** where the price drops to **$389.99** per ad

These details are regularly renegotiated, so we need the pricing rules to be as flexible as possible as they can **change** in the future with little notice.

The interface to our checkout looks like this pseudocode:

```
Checkout co = Checkout.new(pricingRules)
co.add(item1)
co.add(item2)
co.total()
```

## Example scenarios

```
Customer: default
Items: `classic`, `standout`, `premium`
Total: $987.97

Customer: SecondBite
Items: `classic`, `classic`, `classic`, `premium`
Total: $934.97

Customer: Axil Coffee Roasters
Items: `standout`, `standout`, `standout`, `premium`
Total: $1294.96
```
## Tips
We value work-life balance and do not want you to lose a weekend trying to solve this problem. Only spend enough time required to produce an appropriate, clean, testable and maintainable solution to the stated
problem. You should focus on delivering a back-end implementation only. Keep it simple.

Please bring your laptop to the interview, or let us know beforehand if you would like us to provide one.

Good luck, have fun with it, and we look forward to meeting you soon!


## Additional Requirements
1. High priority pricing rule
2. List all items' price after discount
3. Can get 1 Jora membership for free when purchased every another 10 items.
4. List which discount rule applied to each item
