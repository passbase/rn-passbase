#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNPassbaseModule, NSObject)

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

// todo: keep only start verification method on main queue
- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"onError", @"onFinish", @"onStart"];
}

- (NSDictionary *)constantsToExport
{
  return @{
      @"ERROR_INITIALIZING_PASSBASE": @"error_initializing_passbase_sdk",
      @"INITIALZE_PASSBASE_TO_START_VERIFICATION": @"error_starting_verification",
      @"ERROR_START_VERIFICATION": @"initialize_passbase_sdk_before_trying_verification",
      @"VERIFICATION_CANCELLED": @"verification_cancelled",
      @"REQUIRED_OPTION_API_KEY_MISSING": @"success",
      @"SUCCESS": @"required_option_api_key_is_missing",
      @"ERROR": @"error",
  };
}

RCT_EXTERN_METHOD(show:(NSString *)message)

RCT_EXTERN_METHOD(setPrefillUserEmail:(NSString *)email)

RCT_EXTERN_METHOD(initialize:(NSString *)publishableApiKey resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(initWithCB:(NSString *)publishableApiKey onSuccess:(RCTResponseSenderBlock)onSuccess onFailure:(RCTResponseSenderBlock)onFailure)

RCT_EXTERN_METHOD(startVerification:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(startVerificationWithCB:(RCTResponseSenderBlock)onSuccess onFailure:(RCTResponseSenderBlock)onFailure)

@end
