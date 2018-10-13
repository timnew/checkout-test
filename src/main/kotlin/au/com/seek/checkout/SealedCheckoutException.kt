package au.com.seek.checkout

class SealedCheckoutException : RuntimeException("Cannot update sealed checkout")