#### Troubleshooting

Quota:
```
"message": "You exceeded your current quota, please check your plan and billing details.",
"code": "insufficient_quota"
```
Solution:
```
1. Check billing status
Go to https://platform.openai.com/account/usage and see:

How much quota you’ve used.

Whether you have any quota at all.

2. Add billing details
If no quota is showing:

Go to https://platform.openai.com/account/billing/overview

Add a payment method and select a paid plan (e.g., Pay-as-you-go).

3. Check organization context
Sometimes users accidentally:

Create a key for a different org without credit.

Have multiple orgs, but their key is tied to the wrong one.

You can verify the org in: https://platform.openai.com/account/org-settings

```
